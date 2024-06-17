const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { rules, plugins } = require('eslint-config-react-app');

module.exports = {
    entry:'./src/index.js',
    output: {
        path: path.resolve(__dirname,'webApp'),
        filename: 'bundle.js'
    },
    module:{
        rules:[
            {
                test: /\js$/,
                exclude: /node_modules/,
                use: {
                    loader:'babel-loader'
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader','css-loader']
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './src/index.html'
        })
    ],
    devServer: {
        static: {
            directory: path.join(__dirname, 'webApp'),
        },
        compress: true,
        port: 9001
    }
};