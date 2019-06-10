import React, { Component } from "react";
import { Link } from "react-router-dom";
import { Nav, Navbar, NavItem } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import "./App.css";
import Routes from "./Routes";
import { DropdownMenu, MenuItem } from "react-bootstrap-dropdown-menu";

class App extends Component {
  constructor(props) {
    super(props);

    let initialUserStates = {
      token: "",
      demonstrator: "",
      id: "",
      name: "",
      firstName: "",
      lastName: ""
    };
    const savedUserStates = localStorage.getItem("userStates");
    //localStorage.clear();
    if (savedUserStates) {
      initialUserStates = JSON.parse(savedUserStates);
    }
    this.state = {
      userStates: initialUserStates
    };
  }

  updateUserStates = states => {
    localStorage.setItem("userStates", JSON.stringify(states));
    this.setState({ userStates: states });
  };

  logout = () => {
    this.updateUserStates({ token: "" });
  };

  guestNavbar = () => {
    if (this.state.userStates.token.length === 0) {
      return [
        <LinkContainer to="/signup">
          <NavItem>Regisztrálás</NavItem>
        </LinkContainer>,
        <LinkContainer to="/login">
          <NavItem>Bejelentkezés</NavItem>
        </LinkContainer>
      ];
    }
  };

  userNavbar = () => {
    if (this.state.userStates.token.length > 0) {
      const { demonstrator } = this.state.userStates;
      const currentUserName =
        demonstrator.name.firstName + " " + demonstrator.name.lastName;

      return [
        <LinkContainer to="/newsubject">
          <NavItem>Tantargyak</NavItem>
        </LinkContainer>,
        <DropdownMenu userName={currentUserName}>
          <MenuItem text="Edit Profile" location="/profile" />
          <MenuItem text="Change Password" location="/change-password" />
          <MenuItem text="Logout" onClick={this.logout} />
        </DropdownMenu>
      ];
    }
  };

  render() {
    const childProps = {
      userStates: this.state.userStates,
      updateUserStates: this.updateUserStates
    };

    return (
      <div className="App container">
        <Navbar fluid collapseOnSelect>
          <Navbar.Header>
            <Navbar.Brand>
              <Link to="/">Sapientia</Link>
            </Navbar.Brand>
            <Navbar.Toggle />
          </Navbar.Header>
          <Navbar.Collapse>
            <Nav pullRight>
              {this.guestNavbar()}
              {this.userNavbar()}
            </Nav>
          </Navbar.Collapse>
        </Navbar>
        <Routes childProps={childProps} />
      </div>
    );
  }
}

export default App;
