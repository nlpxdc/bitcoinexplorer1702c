var app = new Vue({
    el: '#app',
    data: {
        txid: '',
        transaction: ''
    },
    mounted(){
        console.log('view mounted');

        var url = new URL(location.href);
        this.txid = url.searchParams.get("txid");
        if (!this.txid) {
            alert('txid null');
            return;
        }

        this.getTxByTxid();
        
    },
    methods: {
        getTxByTxid() {
            axios.get('/transaction/getByTxid', {
                params: {
                    txid: this.txid
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.transaction = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})