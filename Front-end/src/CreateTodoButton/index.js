import React from 'react';
import './CreateTodoButton.css';
import { AiOutlinePlus } from "react-icons/ai";

function CreateTodoButton(props) {
  const onClickButton = () => {
    props.setOpenModal(prevState => !prevState);
  };

  return (
    <button
      className="CreateTodoButton"
      onClick={onClickButton}
    >
      <AiOutlinePlus/>
    </button>
  );
}

export { CreateTodoButton };
