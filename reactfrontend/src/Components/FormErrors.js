import React from 'react';


export const FormErrors = ({formErrors}) =>
  <div className='formErrors mb-3' style={{color:'red', padding: 10}}>
    {Object.keys(formErrors).map((fieldName, i) => {
      if(formErrors[fieldName].length > 0){
        return (
          <p key={i}>{fieldName} {formErrors[fieldName]}</p>
        )        
      } 
      else {
        return '';
      }
    })}
  </div>