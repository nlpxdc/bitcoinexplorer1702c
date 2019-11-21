var app = new Vue({
    el: '#app',
    data: {
        address: '',
        addressInfo: '',
        page: 1,
        txPageinfo: ''
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.address = url.searchParams.get("address");
        if (!this.address) {
            alert('address null');
            return;
        }

        var qrcode = new QRCode("AddressQRCode", {
            text: this.address,
            width: 128,
            height: 128,
            colorDark: "#000000",
            colorLight: "#ffffff",
            correctLevel: QRCode.CorrectLevel.H
        });

        this.getAddressInfoByAddress();
        this.getTransactionsByAddress();
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
                    alert('error, pls check ur parameters');
                });
        },
        getTransactionsByAddress() {
            axios.get('/transaction/getByAddressWithPage', {
                params: {
                    address: this.address,
                    page: this.page
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.txPageinfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        handleCurrentChange(val) {
            console.log('current change');
            this.page = val;
            this.getTransactionsByAddress();
        }
    }
})