IGRAPH_DIR=$(HOME)/gnu

CFLAGS=-I$(IGRAPH_DIR)/include/igraph -fPIC -g
LDFLAGS=-L$(IGRAPH_DIR)/lib -ligraph

SRCS = \
	ganalyze.c

OBJS=$(SRCS:.c=.o)

all : ganalyze

ganalyze : $(OBJS)
	$(CC) $(OBJS) $(CFLAGS) $(LDFLAGS) -o $@ 

clean:
	$(RM) $(OBJS) ganalyze
