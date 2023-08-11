import React, { useState, useEffect } from 'react';

function BooksList() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    const url = 'http://localhost:8080/api/books';

    fetch(url)
      .then(response => response.json())
      .then((data) => setBooks(data._embedded.bookDTOForBookList))
      .catch(error => console.error('Exception of getting books list:', error));
  }, []);

  return (
    <div>
      <h1>Books</h1>
      <table class="table table-striped">
      <tbody>
      {books.map(book => (
        <tr key={book.id}>
          <th>{book.title}</th>
          <th>{book.author}</th>
          <th>{book.year}</th>
        </tr>
        ))}
      </tbody>
</table>
    </div>
  );
}

export default BooksList;
