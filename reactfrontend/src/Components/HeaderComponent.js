import React, {Component} from 'react';
import {Navbar, NavbarBrand, Nav,NavbarToggler, Collapse, NavItem, Button} from 'reactstrap';
import { NavLink, withRouter, Link } from 'react-router-dom';

import logo from '../assets/images/logo.jpg'

class Header extends Component {

    constructor(props){
        super(props);
        this.state = { 
            isNavOpen: false,

        };
        this.toggleNav = this.toggleNav.bind(this);
        this.handleLogOut = this.handleLogOut.bind(this);
    }

    toggleNav(){
        this.setState({ //swapping the value, if it is false, change to true
            isNavOpen: !this.state.isNavOpen
        });
    }

    handleLogOut(){
        sessionStorage.clear();
        window.location.reload(false);
        //this.props.location.push("http://localhost:8080")
    }

    render(){
        return ( //short form for react fragment, <div> will have a node in the dom but fragment dont

            <React.Fragment> 
                <Navbar dark expand="md"> 
                    <div className = "container">
                        <NavbarToggler onClick={this.toggleNav} />
                        <NavbarBrand className="mr-auto" href = "/">
                            <img src={logo} height="40" width="41"
                                alt="blank" /> Cap System
                        </NavbarBrand>
                        <Collapse isOpen={this.state.isNavOpen} navbar>
                            <Nav navbar>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin" exact={true}>
                                        Dashboard
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/student">
                                        View Students
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/courselist">
                                        View Courses
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/lecturer">
                                        View Lecturers
                                    </NavLink>
                                </NavItem>
                            </Nav>
                            <Nav className='ml-auto' navbar>
                                <NavItem>
                                    <Button outline onClick={this.handleLogOut}><span className="fa fa-sign-in fa-lg"></span> Log Out</Button>
                                </NavItem>
                            </Nav>
                        </Collapse>    
                    </div>
                </Navbar>

            </React.Fragment>
        );
    }
}

export default withRouter(Header);