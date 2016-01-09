args=(commandArgs(TRUE))

##args is now a list of character vectors
## First check to see if arguments are passed.
## Then cycle through each element of the list and evaluate the
## expressions.
if(length(args) != 3L)
  stop("graphml and output.csv must be specified")

infile <- args[1]
outfile <- args[2]
mon <- args[3]

library(igraph)
graph <- read.graph(infile, "graphml")
ids <- get.vertex.attribute(graph,"id")
betweenness <- betweenness(graph, directed=TRUE)
indegree <- degree(graph, V(graph), "in")
outdegree <- degree(graph, V(graph), "out")
alldegree <- degree(graph, V(graph), "all")
closeness <- degree(graph)
closeness <- closeness(graph)
f1 <- data.frame(mon,ids, betweenness, indegree, outdegree, alldegree, closeness)
write.table(f1, file=outfile)
# library(rgl)
# coords <- layout.kamada.kawai(graph, dim=3)
# open3d()
# rglplot(graph, vertex.size=3, vertex.label=NA, edge.arrow.size=0.6, layout=coords)
# pdf("term-network.pdf")
# plot(graph, layout=layout.fruchterman.reingold)
# dev.off()
clusters(graph)
#c <- edge.betweenness.community(graph)
#print(c)
plot(graph, vertex.size=0, vertex.label=NA, edge.arrow.size=0.6)

