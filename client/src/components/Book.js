import React, { useState, useEffect } from 'react';

function Book({ id }) {
    const [book, setBook] = useState([]);

    useEffect(() => {
        const url = `http://localhost:8080/api/books/${id}`;

        fetch(url)
            .then(response => response.json())
            .then((data) => setBook(data))
            .catch(error => console.error('Exception of getting book:', error));
    }, []);

    return (
        <div>
            <h1>{book.title}</h1>
            <h2>{book.author}</h2>
        </div>
    );
}

export default Book;