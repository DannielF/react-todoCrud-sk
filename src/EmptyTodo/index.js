import React from 'react';
import { SiAngellist } from "react-icons/si";
import './EmptyTodo.css';

function EmptyTodo() {
  return (
    <>
      <p className='emptyTodo'>Crea tu primer TODO <SiAngellist/> </p>
    </>
  )
}

export { EmptyTodo };
