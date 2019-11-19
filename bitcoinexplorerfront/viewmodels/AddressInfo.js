var app = new Vue({
    el: '#app',
    data: {
        address: '',
        addressInfo: ''
    },
    mounted(){
        console.log('view mounted');

        var url = new URL(location.href);
        this.address = url.searchParams.get("address");
        if(!this.address){
            alert('address null');
            return;
        }

        this.getAddressInfoByAddress();
    },
    methods: {
        getAddressInfoByAddress() {
            axios.get('/address/getInfoByAddress', {
                params: {
                    address: this.address
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.addressInfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})