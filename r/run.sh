for i in `ls -1 /home/vavasthi/fpmi/research/code/tacitknowledge/graphs/phrase/monthly/sci.physics/*.graphml`
do 
echo "The month is" $MONTH
MONTH=`echo $i|cut -c127-133`
Rscript command.r $i $MONTH.csv $MONTH
done
