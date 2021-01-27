module.exports = {
    devServer: {
        proxy: 'http://localhost:8888/api',
        host: 'localhost',
        port: 8080,
    }
}