var app = new Vue({
    el: '#app',
    data: {
        blockPage: '',
        page: 1
    },
    computed: {
        blocks() {
            return this.blockPage ? this.blockPage.list : [];
        },
        newBlocks() {
            return this.blocks.map(block => {
                var newBlock = block;
                newBlock.fornow = moment.unix(block.time).fromNow();
                return newBlock;
            });
        }
    },
    mounted() {
        this.getBlocks();
    },
    methods: {
        getBlocks() {
            axios.get('/block/getWithPage', {
                params: {
                    page: this.page
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.blockPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        handleCurrentChange(val) {
            console.log('current change');
            this.page = val;
            this.getBlocks();
        }
    }
})