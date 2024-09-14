import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit{
  bookRequest: BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };
  errorMsg: Array<string> = [];
  selectedBookCover: any;
  selectedPicture: string | undefined;

  constructor(
    private bookService: BookService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.findBookById({
        "book-id": bookId
      }).subscribe({
        next: (book) => {
          this.bookRequest =  {
            id: book.id,
            title: book.title as string,
            authorName: book.authorName as string,
            isbn: book.isbn as string,
            synopsis: book.synopsis as string,
            shareable: book.shareable,
          }
          if (book.cover) {
            this.selectedPicture = 'data:image/jpg;base64,' + book.cover;
          }
        }
      })
    }
  }

  onFileSelected(event: any) { // the event is the selected file
    this.selectedBookCover = event.target.files[0]; // we want only 1 file
    console.log(this.selectedBookCover);
    if (this.selectedBookCover) {
      const reader = new FileReader();
      reader.onload = () => { // we need to wait for the loading of the picture
        this.selectedPicture = reader.result as string; // in order to display it dynamically
      }
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  saveBook() {
    // console.log(this.bookRequest)
    this.bookService.saveBook({
      body: this.bookRequest
    }).subscribe({
      // once we have the bookId when the book get saved, we want to upload the book cover
      // because we need to wait for the book to be saved and then upload the cover
      next: (bookId) => {
        this.bookService.uploadBookCoverPicture({
          'book-id': bookId,
          body: {
            file: this.selectedBookCover
          }
        }).subscribe({
          next: (book) => {
            this.router.navigate(['/books/my-books']);
          }
        });
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    })
  }
}
