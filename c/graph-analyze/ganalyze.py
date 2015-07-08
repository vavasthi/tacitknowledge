#!/usr/bin/python
import igraph
import optparse
import sys

def processGraphFile(filename) :
    g = igraph.Graph.Read(filename, 'graphml')
    od = g.average_path_length()
    print od
    el = g.get_edgelist()
    

def main(argv) :
    
    parser = optparse.OptionParser(conflict_handler="resolve")
    parser.add_option('-f', '--file', dest="file",
                      help="The graphml file", default="admin")

    options, remainder = parser.parse_args()
    _options = dict()
    _options['file'] = options.file
    processGraphFile(_options['file'])

if __name__ == '__main__' :
    main(sys.argv[1:])
__author__ = 'Vinay Avasthi'
