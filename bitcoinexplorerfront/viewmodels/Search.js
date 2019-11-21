var app = new Vue({
    el: '#app',
    data: {
        keyword: ''
    },
    methods: {
        handleSearchClick() {
            console.log('search click');

            if (!isNaN(this.keyword)) {
                console.log('go to block show page');
                this.getBlockhashByHeight();
                return;
            }

            if (this.keyword.length < 64) {
                console.log('go to address info page');
                location.href = 'AddressInfo?address=' + this.keyword;
                return;
            }

            if (this.keyword.startsWith('00000')) {
                console.log('go to block show page');
                location.href = 'BlockShow?blockhash=' + this.keyword;
                return;
            } else {
                console.log('go to tx show page');
                location.href = 'TransactionShow?txid=' + this.keyword;
                return;
            }
        },
        getBlockhashByHeight() {
            axios.get('/block/getBlockhashByHeight', {
                params: {
                    height: this.keyword
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.keyword = response.data;
                    location.href = 'BlockShow?blockhash=' + app.keyword;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})