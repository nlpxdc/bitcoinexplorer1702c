var app = new Vue({
    el: '#app',
    data: {
        blocks: []
    },
    mounted(){
        this.getRecentBlocks();
    },
    methods: {
        getRecentBlocks() {
            axios.get('/block/getRecent')
                .then(function (response) {
                    console.log(response);
                    app.blocks = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})