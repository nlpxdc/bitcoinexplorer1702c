var app = new Vue({
    el: '#app',
    data: {
        blockhash: '',
        block: '',
        page: 1,
        txPageinfo: ''
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.blockhash = url.searchParams.get("blockhash");
        if (!this.blockhash) {
            alert('blockhash null');
            return;
        }

        this.getBlockByBlockhash();
        this.getTransactionsByBlockhash();

    },
    methods: {
        getBlockByBlockhash() {
            axios.get('/block/getInfoByHash', {
                params: {
                    blockhash: this.blockhash
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.block = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        getTransactionsByBlockhash() {
            axios.get('/transaction/getByBlockhashWithPage', {
                params: {
                    blockhash: this.blockhash,
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
        handleCurrentChange(val){
            console.log('current change');
            this.page = val;
            this.getTransactionsByBlockhash();
        }
    }
})