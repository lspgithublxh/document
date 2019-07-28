function getFile(){
  for element in `ls $1`
     do
      dir_or_file=$1$element
      if [ -d $dir_or_file ]
        then
          getFile $dir_or_file
        else
          echo $dir_or_file
          sz $dir_or_file
      fi
     done
}
root_dir="/home/hdp_hbg_fcrd_58/hugwidebizjob/1/"
getFile $root_dir