import React, {Component} from 'react';
import './App.css';
import { BrowserRouter } from 'react-router-dom';

class App extends Component {
  state = {
    students: []
  };

  async componentDidMount() {
    const response = await fetch('/admin/student');
    const body = await response.json();
    this.setState({students: body});
  }

  render() {
    const {students} = this.state;
    return (
        <div className="App">
          <header className="App-header">
            <div className="App-intro">
              <h2>Student (Testing Link)</h2>
              {students.map(student =>
                  <div key={student.id}>
                    {student.name} ({student.email})
                  </div>
              )}
            </div>
          </header>
        </div>
    );
  }
}
export default App;
