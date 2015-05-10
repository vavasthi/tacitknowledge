IGRAPH_DIR=$(HOME)/gnu

CXXFLAGS=-I$(IGRAPH_DIR)/include/igraph -fPIC -g
LDFLAGS=-L$(IGRAPH_DIR)/lib -ligraph

SRCS = \
	ganalyze.cpp

OBJS=$(SRCS:.cpp=.o)

all : ganalyze

ganalyze : $(OBJS)
	$(CXX) $(OBJS) $(CFLAGS) $(LDFLAGS) -o $@ 

clean:
	$(RM) $(OBJS) ganalyze
