var app = new Vue({
    el: '#app',
    data: {
        transactions: []
    },
    mounted() {
        console.log("view mounted");
        // this.bitcoinPushConnect();
    },
    methods: {
        bitcoinPushConnect() {
            console.log('bitcoin push connecting');

            this.socket = new SockJS('http://localhost:8080/bitcoinexplorerpush');
            this.stompclient = Stomp.over(this.socket);
            this.stompclient.connect({}, frame => {
                console.log(frame);
                this.stompclient.subscribe('/bitcoin/deltaTx', function (data) {
                    console.log(data);
                    var newTransactions = JSON.parse(data.body);
                    app.transactions = newTransactions.concat(app.transactions);
                    //[...newTransactions, ...this.transactions];  spread syntax
                });
            });

        },
        handleConnect() {
            console.log('connect click');
            this.bitcoinPushConnect();
        }
    }
})