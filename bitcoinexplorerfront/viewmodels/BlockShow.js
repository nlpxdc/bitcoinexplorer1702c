var app = new Vue({
    el: '#app',
    data: {
        blockhash: '',
        block: '',
    },
    mounted(){
        console.log('view mounted');

        var url = new URL(location.href);
        this.blockhash = url.searchParams.get("blockhash");
        if (!this.blockhash) {
            alert('blockhash null');
            return;
        }

        this.getBlockByBlockhash();

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
        }
    }
})