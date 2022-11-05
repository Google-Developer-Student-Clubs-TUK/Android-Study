import express from 'express';
import bodyParser from 'body-parser';
import userRouter from '/routes/user.js'
import postRouter from '/routes/post.js'
import commentRouter from '/routes/comment.js'


const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use('/user', userRouter)
app.use('/post', postRouter);
app.use('/comment', commentRouter);

app.listen(3000, 'localhost', function(){
    console.log('서버 실행중');
});


