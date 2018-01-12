tr '[:upper:]' '[:lower:]' < ./words.txt > ./temp.dict
cat ./temp.dict | sort | uniq > ./words.dict
rm -f ./temp.dict
