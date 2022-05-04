import React, { useState, useEffect } from 'react';

const TodoContext = React.createContext();

function TodoProvider(props) {
  const API = 'http://localhost:8080/todo';

  /**
   * Local todo state
   */
  const [todos, saveTodos] = useState([]);

  /**
   * Method GET to list all todos
   * assign todos to state
   */
  const fetchApi = async () => {
    const options = {
      method: 'GET',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'same-origin',
    };
    try {
      const reponse = await fetch(API, options);
      const responseData = await reponse.json();
      saveTodos(responseData.data);
    } catch (error) {
      console.error('Fetch -GET- error', error);
    }
  };

  useEffect(() => {
    fetchApi();
  }, [todos]);

  // states for modal and search input
  const [searchValue, setSearchValue] = React.useState('');
  const [openModal, setOpenModal] = React.useState(false);

  // Show how many todos are completed
  const completedTodos = todos.filter((todo) => !!todo.completed).length;
  const totalTodos = todos.length;

  let searchedTodos = [];

  if (!searchValue.length >= 1) {
    searchedTodos = todos;
  } else {
    searchedTodos = todos.filter((todo) => {
      const todoText = todo.text.toLowerCase();
      const searchText = searchValue.toLowerCase();
      return todoText.includes(searchText);
    });
  }

  /**
   * Add a todo to the db
   * @param {*} text String
   */
  const addTodo = async (text) => {
    const newTodo = {
      text,
      completed: false,
    };

    const options = {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'same-origin',
      body: JSON.stringify(newTodo),
    };

    try {
      await fetch(API, options);
    } catch (error) {
      console.error('Fetch -POST- error', error);
    }
  };

  /**
   * Set a todo as completed
   * @param {*} id Integer
   */
  const completeTodo = async (id) => {
    const options = {
      method: 'PATCH',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'same-origin',
    };
    console.log(`${API}/completed/${id}`);
    try {
      await fetch(`${API}/completed/${id}`, options);
    } catch (error) {
      console.error('Fetch -PATCH- error', error);
    }
  };

  /**
   * Delete a todo
   * @param {*} id Integer
   */
  const deleteTodo = async (id) => {
    const options = {
      method: 'DELETE',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'same-origin',
    };
    console.log(`${API}/${id}`);
    try {
      await fetch(`${API}/${id}`, options);
    } catch (error) {
      console.error('Fetch -DELETE- error', error);
    }
  };

  return (
    <TodoContext.Provider
      value={{
        totalTodos,
        completedTodos,
        searchValue,
        setSearchValue,
        searchedTodos,
        addTodo,
        completeTodo,
        deleteTodo,
        openModal,
        setOpenModal,
      }}
    >
      {props.children}
    </TodoContext.Provider>
  );
}

export { TodoContext, TodoProvider };
