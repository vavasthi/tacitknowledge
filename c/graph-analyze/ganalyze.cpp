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
  long int i;
  for (i=0; i<igraph_vector_size(v); i++) {
    printf(" %li", (long int) VECTOR(*v)[i]);
  }
  printf("\n");
}

bool 
processGraph(const std::string& fileName) {
  igraph_t g;
  igraph_error_handler_t* oldhandler;
  igraph_warning_handler_t* oldwarnhandler;
  int result;
  FILE *ifile, *ofile;
  igraph_real_t centralization;
  igraph_real_t theoterical_max;
  igraph_vector_t res;
  igraph_vector_init(&res, 0);

  /* GraphML */
  ifile=fopen(fileName.c_str(), "r");
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
  /*
  ofile=fopen("test2.gxl", "w");
  if (ofile) {
    if ((result=igraph_write_graph_graphml(&g, ofile,  1))) {
      return 1;
    }
    fclose(ofile);
    unlink("test2.gxl");
  }
  */
  long vcount = (long int) igraph_vcount(&g);
  long ecount = (long int) igraph_ecount(&g);
  
  printf("The directed graph:\n");
  printf("Vertices: %li\n", vcount);
  printf("Edges: %li\n", ecount);
  printf("Directed: %i\n", (int) igraph_is_directed(&g));
  igraph_write_graph_edgelist(&g, stdout);
  
  igraph_vector_reserve(&res, vcount);

  igraph_centralization_betweenness(&g, 
                                    &res, 
                                    IGRAPH_IN, 
                                    IGRAPH_NO_LOOPS, 
                                    &centralization, 
                                    &theoterical_max, 
                                    1);

  
  printVector(&res);
  
  igraph_centralization_degree(&g, 
                               0, 
                               IGRAPH_IN, 
                               IGRAPH_NO_LOOPS, 
                               &centralization, 
                               &theoterical_max, 
                               1);

  printf("%f\n", centralization);
  
  igraph_destroy(&g);
  
  
  igraph_set_warning_handler(oldwarnhandler);

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
    
    if ((dp = readdir(dirp)) != NULL && dp->d_type == DT_REG) {
      
      list.push_back(dp->d_name);
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
