for i in `ls -1 /home/vavasthi/fpmi/research/code/tacitknowledge/graphs/monthly/sci.physics/*.graphml`
do 
MONTH=`echo $i|cut -c120-129`
Rscript command.r $i $MONTH.csv $MONTH
done
