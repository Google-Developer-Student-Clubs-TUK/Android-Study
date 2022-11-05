import { reqDB } from '../request.js'
import express from 'express';

const router = express.Router();
export default router;

router.post('/comment/entryComment',  async(req, res)=> {
    console.log(req.body);

    let postId = req.body.postId
    let userId = req.body.uId;
    let content = req.body.content;

    let sql = 'INSERT INTO Content (postId, userId, content, createdAt) values (?, ?, NOW())'
    let params = [postId, userId, content, 0]

    let response = await reqDB(sql, params);
    res.json(response);
});

router.get('post/getCommentByPostId', async(req, res) =>{
    console.log(req.query);

    let postId = req.query.uId;
    let sql = 'SELECT User.nickname as nickname, Comment.content as content, Comment.createdAt as createdAt FROM Comment, User where Comment.postId=? and User.uId = ? and Comment.userId = User.uId Order by Comment.createdAt ASC'
    let params = [postId]

    let response = await reqDB(sql, params);
    res.json(response);
})
