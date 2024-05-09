var pop;

function showPopup(index){
    pop = document.getElementById(index);
    pop.classList.add('active');
}
function closePopup(){
    if(!pop) return;
    pop.classList.remove('active');
    pop = null;
}