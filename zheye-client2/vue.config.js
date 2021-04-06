module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy:'http://localhost:8888/api'

    }
}