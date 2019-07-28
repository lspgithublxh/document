BEGIN{
 count=0
 printf "-------------------start"
}
{
 count+=$1
# printf count
# printf "--"
}
END{
 printf "-------------------end"
 printf "TOTAL:"
 printf count
 printf "-------end"
}