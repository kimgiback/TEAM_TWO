function showContents(){
    let subjectBox = document.getElementsByClassName('subjectBox');
    let myInquiry = document.getElementsByClassName('myInquiry');    
    
    for(let i = 0; i < subjectBox.length; i++) {
        subjectBox[i].addEventListener('click', function(){                             
            myInquiry[i].classList.add('active');
        });
    }
}

function closeContents(){
    let allCloseBtn = document.getElementById('closeMyInquiry');
    let myInquiry = document.getElementsByClassName('myInquiry'); 

    allCloseBtn.addEventListener('click', function() {
        for(let i = 0; i < myInquiry.length; i++) {
            myInquiry[i].classList.remove('active');
        }
    });
}


