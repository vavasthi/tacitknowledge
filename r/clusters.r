library(igraph)
graph <- read.graph(infile, "graphml")
clusters(graph)
