window.onload = function(){
    var card = new Card({
        // a selector or DOM element for the form where users will
        // be entering their information
        form: '.cardwrapper1', // *required*
        // a selector or DOM element for the container
        // where you want the card to appear
        container: '.cardwrapper', // *required*

        masks: {
            cardNumber: '•' // optional - mask card number
        },
    });
}