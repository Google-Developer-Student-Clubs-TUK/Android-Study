import { reqDB } from '../request.js'
import express from 'express';

const router = express.Router();
export default router;

router.post('/post/entryPost',  async(req, res)=> {
    console.log(req.body);

    let userId = req.body.uId;
    let content = req.body.content;

    let sql = 'INSERT INTO POST (userId, content, createdAt) values (?, ?, NOW())'
    let params = [userId, content, 0]

    let response = await reqDB(sql, params);
    res.json(response);
});

router.post('/post/postImg', async(req, res)=>{
    console.log(req.body)

    let postId = req.body.postId
    let img = req.body.imgUrl
    let sql = 'INSERT INTO PostImg (postId, imageUrl) values (?, ?)'

    let params = [];
    img.forEach(element => {
        params.push([postId, element])
    });

    let response = reqDB(sql, params)
        res.json(response)
})

router.get('post/getPostByUId', async(req, res) =>{
    console.log(req.query);

    let userId = req.query.uId;
    let sql = 'SELECT * FROM POST where userId=?'
    let params = [userId]

    let response = await reqDB(sql, params);
    res.json(response);
})

router.get('post/getPost', async(req, res) =>{
    console.log(req.query);

    let sql = 'SELECT * FROM POST';
    let params = [];

    let response = await reqDB(sql, params);
    res.json(response);
})