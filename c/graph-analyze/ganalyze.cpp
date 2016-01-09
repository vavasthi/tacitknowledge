#include <igraph.h>
#include <stdio.h>
#include <unistd.h>		/* unlink */
#include <dirent.h>

#include <list>
#include <string>
#include <iostream>

void custom_warning_handler (const char *reason, const char *file,
			     int line, int igraph_errno) {
  printf("Warning: %s\n", reason);
}

void 
printVector(igraph_vector_t *v) {
  print_v
  for (long i=0; i<igraph_vector_size(v); i++) {
    std::cout << (long int) VECTOR(*v)[i] << ' ';
  }
  std::cout << std::endl;
}


void showMultilevelModularityResults(igraph_t *g, 
                                     igraph_vector_t *membership, 
                                     igraph_matrix_t *memberships, 
                                     igraph_vector_t *modularity,
                                     std::ostream& strm) {
  
  long int i, j, no_of_nodes = igraph_vcount(g);
  
  j=igraph_vector_which_max(modularity);
  strm << "Membership ";
  printVector(membership);
  strm << "Modularity ";
  printVector(modularity);
  for (i=0; i<igraph_vector_size(membership); i++) {
    if (VECTOR(*membership)[i] != MATRIX(*memberships, j, i)) {
      strm << "WARNING: best membership vector element "
           << i << " does not match the best one in the membership matrix\n"
           << std::endl;
    }
  }
  for (i=0; i < igraph_matrix_nrow(memberships); i++) {
    for (j=0; j < no_of_nodes; j++) {
      strm << (long int)MATRIX(*memberships, i, j) << ' ';
    }
    strm << std::endl;
  }
  strm << std::endl;
}

int
printAttributeList(const igraph_t* g){

  igraph_vector_t gtypes, vtypes, etypes;
  igraph_strvector_t gnames, vnames, enames;
  long int i;

  igraph_vector_t vec;
  igraph_strvector_t svec;
  long int j;

  igraph_vector_init(&gtypes, 0);
  igraph_vector_init(&vtypes, 0);
  igraph_vector_init(&etypes, 0);
  igraph_strvector_init(&gnames, 0);
  igraph_strvector_init(&vnames, 0);
  igraph_strvector_init(&enames, 0);

  igraph_cattribute_list(g, &gnames, &gtypes, &vnames, &vtypes, 
			 &enames, &etypes);


  igraph_strvector_destroy(&svec);
  igraph_vector_destroy(&vec);

  igraph_strvector_destroy(&enames);
  igraph_strvector_destroy(&vnames);
  igraph_strvector_destroy(&gnames);
  igraph_vector_destroy(&etypes);
  igraph_vector_destroy(&vtypes);
  igraph_vector_destroy(&gtypes);

  return 0;
}

int
readGraphFromFile(const std::string& filename, igraph_t& g){

  FILE *ifile, *ofile;
  igraph_error_handler_t* oldhandler;
  igraph_warning_handler_t* oldwarnhandler;
  int result;

  /* GraphML */
  ifile=fopen(filename.c_str(), "r");
  if (ifile==0) {
    return false;
  }
  
  oldhandler=igraph_set_error_handler(igraph_error_handler_ignore);
  oldwarnhandler=igraph_set_warning_handler(custom_warning_handler);
  if ((result=igraph_read_graph_graphml(&g, ifile, 0))) {
    // maybe it is simply disabled at compile-time
    if (result == IGRAPH_UNIMPLEMENTED) return 77;
    return 1;
  }
  igraph_set_error_handler(oldhandler);

  fclose(ifile);
  return 0;
}

void
processMultilevelModularity(const std::string& filename)
{

  igraph_t g;
  
  readGraphFromFile(filename, g);
  igraph_to_undirected(&g, IGRAPH_TO_UNDIRECTED_MUTUAL, 0);
  
  igraph_vector_t modularity, membership, edges;
  igraph_matrix_t memberships;
  
  igraph_vector_init(&modularity, 0);
  igraph_vector_init(&membership, 0);
  igraph_matrix_init(&memberships, 0, 0);
  
  igraph_community_multilevel(&g, 0, &membership, &memberships, &modularity);
  showMultilevelModularityResults(&g, &membership, &memberships, &modularity, std::cout);
  igraph_destroy(&g);
  
}

bool 
processGraph(const std::string& fileName) {
  igraph_t g;
  int result;
  igraph_real_t centralization_betweenness;
  igraph_real_t centralization_degree;
  igraph_real_t theoterical_max;
  igraph_vector_t res;
  igraph_vector_ptr_t cliques;
  igraph_vector_init(&res, 0);

  readGraphFromFile(fileName, g);
  
  long vcount = (long int) igraph_vcount(&g);
  long ecount = (long int) igraph_ecount(&g);
  
  std::cout << "Directed graph " 
            << "Vertices: " <<  vcount
            << "Edges: " << ecount
            << " Directed:" << (int) igraph_is_directed(&g)
            << std::endl;
  //  igraph_write_graph_edgelist(&g, stdout);
  
  //printAttributeList(&g);
  
  igraph_vector_reserve(&res, vcount);

  igraph_centralization_betweenness(&g, 
                                    &res, 
                                    IGRAPH_IN, 
                                    IGRAPH_NO_LOOPS, 
                                    &centralization_betweenness, 
                                    &theoterical_max, 
                                    1);

  igraph_centralization_betweenness(&g, 
                                    &res, 
                                    IGRAPH_IN, 
                                    IGRAPH_NO_LOOPS, 
                                    &centralization_degree, 
                                    &theoterical_max, 
                                    1);

  std::cout << "Centralization Betweenness " << centralization_betweenness << " Centralization Degree " << centralization_degree << std::endl;
  

  igraph_vector_ptr_init(&cliques, 0);
  
  igraph_largest_cliques(&g, &cliques);

  int vectorSize = igraph_vector_ptr_size(&cliques);
  std::cout << "Number of Cliques = " << vectorSize << std::endl;
  
  for (long i = 0; i < vectorSize; ++i) {
    igraph_vector_t* v = reinterpret_cast<igraph_vector_t*>(igraph_vector_ptr_e(&cliques, i));
    printVector((igraph_vector_t*)v);
    igraph_vector_destroy(v);
    free(v);
  }
  
  processMultilevelModularity(fileName);
  
  
  // igraph_destroy(&g);
  
  
  //  igraph_set_warning_handler(oldwarnhandler);

  /* There were sometimes problems with this file */
  /* Only if called from R though, and only on random occasions, once in every 
     ten reads. Do testing here doesn't make much sense, but if we have the file 
     then let's do it anyway. */
  return true;
}

const std::list<std::string>&
getListOfDirectories(const std::string& basedir,
                     std::list<std::string>& list) {
  
  DIR *dirp;
  if ((dirp = opendir(basedir.c_str())) == NULL) {
    std::cerr << "Couldn't open " << basedir << std::endl;
    return list;
  }
  struct dirent *dp = NULL;
  do {
    
    if ((dp = readdir(dirp)) != NULL && dp->d_type == DT_DIR) {
      
      list.push_back(dp->d_name);
    }
  } while(dp != NULL);
  closedir(dirp);
  return list;
}

const std::list<std::string>&
getListOfFiles(const std::string& basedir,
               std::list<std::string>& list) {
  
  DIR *dirp;
  if ((dirp = opendir(basedir.c_str())) == NULL) {
    std::cerr << "Couldn't open " << basedir << std::endl;
    return list;
  }
  struct dirent *dp = NULL;
  do {
    
    if ((dp = readdir(dirp)) != NULL && dp->d_type == DT_REG ) {
      std::string name = dp->d_name;
      if (name.find(".graphml") != std::string::npos) {
          
        list.push_back(dp->d_name);
      }
    }
  } while(dp != NULL);
  closedir(dirp);
  return list;
}

int main(int argc, char **argv) {

  if (argc < 2) {
    std::cerr << argv[0] << " base_directory" << std::endl;
    exit(1);
  }
  std::list<std::string> list;
  getListOfDirectories(argv[1],list);
  for (std::list<std::string>::const_iterator csi = 
         list.begin();
       csi != list.end();
       ++csi) {

    std::cout << "Topic is " << *csi << std::endl;
    std::cout << "========================" << *csi << std::endl;
    std::list<std::string> files;
    std::string dir = argv[1];
    dir += "/" + *csi;
    getListOfFiles(dir, files);
    for (std::list<std::string>::const_iterator cfi = 
           files.begin();
         cfi != files.end();
         ++cfi) {
      std::cout << *cfi << std::endl;
      processGraph(dir + "/" + *cfi);
    }
  }
}
