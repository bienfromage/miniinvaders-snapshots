var alphabet = "bacdefghiodb";
var totalScore = 0;
for(var i = 10; i < alphabet.length; i++){
  for(var j = 0; j <= 9; j++){
    if(alphabet.charAt(i) === alphabet.charAt(j)){
      totalScore += j;
      if(i !== (alphabet.length-1)){
        totalScore *= 10;
      }
    }
  }
}
console.log(totalScore);
