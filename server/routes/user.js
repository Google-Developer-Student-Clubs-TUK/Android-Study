import { reqDB } from '../request.js'
import express from 'express';

const router = express.Router();
export default router;

router.post('/user/join', async(req, res)=> {
    console.log(req.body);

    let uId = req.body.uId;
    let nickname = req.body.nickname;
    let email = req.body.email;
    let profileImg = req.body.profileImg;

    let sql = 'INSERT INTO User (userId, nickname, email, profileImg, createdAt) values (?, ?, ?, ?, NOW())'
    let params = [uId, nickname, email, profileImg, 0]
    
    let response = await reqDB(sql, params);

    res.json(response);
});

router.get('/user/getProfile', async(req, res)=> {
    const uId = req.query.uId;
    const sql = 'SELECT * FROM User where uId=?';
    const params =[uId];

    const response = await reqDB(sql, params);

    if(response === null){
        response.msg = '계정 없음'
    }else{
        response.msg = '계정 있음'
    }
    console.log(response.msg)

    res.json(response)
})