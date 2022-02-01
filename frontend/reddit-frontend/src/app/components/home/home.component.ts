import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/common/post';
import { PostService } from 'src/app/service/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  posts!:Post[];

  constructor(private postsService: PostService) { }

  ngOnInit(): void {
    this.postsService.getPostList().subscribe(
      posts => {
        this.posts = posts;
      }
    )
      
    
  }

}
