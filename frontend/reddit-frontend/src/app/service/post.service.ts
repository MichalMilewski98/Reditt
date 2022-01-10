import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Post } from '../common/post';

@Injectable({
  providedIn: 'root',
})

export class PostService {
  private POST_URL = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) {}

  getPostList(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.POST_URL);
  }

  getPost(): Observable<Post> {
    return this.httpClient.get<Post>(this.POST_URL);
  }


}

