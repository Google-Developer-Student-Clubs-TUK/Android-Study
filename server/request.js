import mysql from 'mysql2';
import 'dotenv';

const pool = mysql.createPool({
    host: process.env.HOST,
    user: process.env.USER, 
    database: process.env.DATABASE,
    password: process.env.PASSWORD,
    port: process.env.PORT,
    waitForConnections: true,
    connectionLimit: 10,
    dateStrings: "date"
});

export const reqDB = async (sql, params) => {
    let response = {
        isConnect: false,
        resultCode: 404,
        msg: '연결 실패',
        result: null,
    };
    pool.getConnection((err, connection)=>{
        if(!err){
            connection.query(sql, params, (err, result)=>{
                if(err){
                    response = {
                        isConnect: false,
                        resultCode: err.code,
                        msg: err.message,
                        result: null,
                    };
                    connection.release();
                }else{
                    response = {
                        isConnect: true,
                        resultCode: 200,
                        msg: '연결 성공',
                        result: result,
                    };
                    connection.release();
                }
            });
        }else{
            console.log(err);
            response = {
                isConnect: false,
                resultCode: err.code,
                msg: err.message,
                result: null,
            };
        }
    });
    return response;
}