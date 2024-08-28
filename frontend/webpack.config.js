const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
// Load environment variables from .env file
dotenv.config();
module.exports = {
    entry: path.resolve(__dirname, 'src','index.js'),
    output: {
        path: path.resolve(__dirname, 'webApp'),
        filename: 'bundle.js',
        publicPath: 'auto'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader'
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|jpe?g|gif|svg)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[name].[hash].[ext]',
                            outputPath: 'images',
                        },
                    },
                ],
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'public', 'index.html')
        }),
        new webpack.DefinePlugin({
            'process.env': JSON.stringify(process.env)  
        })
    ],
    devServer: {
        static: {
            directory: path.join(__dirname, 'webApp'),
        },
        compress: true,
        port: 9001,
        historyApiFallback: true,
    },
    performance: {
        hints: false,
        maxEntrypointSize: 512000,
        maxAssetSize: 512000
    }
};