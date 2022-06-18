import React, { Component } from 'react';
import {Button} from 'reactstrap';
import { Link } from 'react-router-dom';

class Home extends Component {


    render(){
        return (
            <Button size="sm" color="primary" tag={Link} to={"/admin/student"}>Go to Student Page</Button>
        )
    }


}
export default Home;