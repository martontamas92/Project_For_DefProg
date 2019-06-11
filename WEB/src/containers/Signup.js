import React, { Component } from "react";
import link from "./Root";
import {
  HelpBlock,
  FormGroup,
  FormControl,
  ControlLabel
} from "react-bootstrap";
import LoaderButton from "../components/LoaderButton";
import "./Signup.css";
import Axios from "axios";
import { Route, Redirect } from "react-router";

export default class Signup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: false,
      email: "",
      lastname: "",
      firstname: "",
      password: "",
      confirmPassword: "",
      confirmationCode: "",
      newUser: null,
      alertmessage: ""
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  validateForm() {
    return (
      this.state.email.length > 0 &&
      this.state.lastname.length > 0 &&
      this.state.firstname.length > 0 &&
      this.state.password.length > 7 &&
      this.state.password === this.state.confirmPassword
    );
  }

  validateConfirmationForm() {
    return this.state.confirmationCode.length > 0;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSubmit(event) {
    //const { email, password, middlename, firstname, lastname } = this.state;
    try {
      const response = Axios.post(
        "http://" + link + "/WS/home/demonstrator/registration",
        //"http://demo7358603.mockable.io/login",
        {
          Name: {
            firstName: this.state.firstname,
            lastName: this.state.lastname
          },
          Auth: { uname: this.state.email, passwd: this.state.password }
        },
        { responseType: "json" }
      ).then(response => {
        const { data } = response;
        let alertmessage = "";

        if (data.name) {
          const {
            name: { firstName, lastName }
          } = data;
          if (firstName) {
            alertmessage += firstName + " ";
          }
          if (lastName) {
            alertmessage += lastName + " ";
          }
          alertmessage += "regisztrált";
        } else {
          if (data.message) {
            alertmessage += data.message;
          }
        }

        this.setState({ alertmessage: alertmessage });

        // console.log(response.data);
        // const object = JSON.stringify(response.data);
        // JSON.parse(object, (key, value) => {
        //   alertmessage += value;
        // });

        // alert(this.state.alertmessage);

        //  if (alertmessage.includes("regisztrált")) {
        //    <Redirect to="/login" />;
        //  }
      });
      // const data = JSON.parse(response);
      console.log(response);
    } catch (err) {
      console.log(err);
    }
    event.preventDefault();
  }

  handleConfirmationSubmit = async event => {
    event.preventDefault();

    this.setState({ isLoading: true });
  };

  renderForm() {
    return (
      <form onSubmit={this.handleSubmit}>
        <FormGroup controlId="firstname" bsSize="large">
          <ControlLabel>Vezetéknév</ControlLabel>
          <FormControl
            autoFocus
            type="text"
            value={this.state.firstname}
            onChange={this.handleChange}
          />
        </FormGroup>
        <FormGroup controlId="lastname" bsSize="large">
          <ControlLabel>Keresztnév</ControlLabel>
          <FormControl
            autoFocus
            type="text"
            value={this.state.lastname}
            onChange={this.handleChange}
          />
        </FormGroup>
        <FormGroup controlId="email" bsSize="large">
          <ControlLabel>Email cím</ControlLabel>
          <FormControl
            autoFocus
            type="email"
            value={this.state.email}
            onChange={this.handleChange}
          />
        </FormGroup>
        <FormGroup controlId="password" bsSize="large">
          <ControlLabel>Jelszó</ControlLabel>
          <FormControl
            value={this.state.password}
            onChange={this.handleChange}
            type="password"
          />
        </FormGroup>
        <FormGroup controlId="confirmPassword" bsSize="large">
          <ControlLabel>Jelszó megerősítése</ControlLabel>
          <FormControl
            value={this.state.confirmPassword}
            onChange={this.handleChange}
            type="password"
          />
        </FormGroup>
        <LoaderButton
          block
          bsSize="large"
          disabled={!this.validateForm()}
          type="submit"
          isLoading={this.state.isLoading}
          text="Regisztrálás"
          loadingText="Signing up…"
        />
      </form>
    );
  }

  render() {
    if (this.state.alertmessage) {
      return <Redirect to="/login" />;
    }
    if (this.props.userStates.token.length > 0) {
      return <Redirect to="/newsubject" />;
    }

    return <div className="Signup">{this.renderForm()}</div>;
  }
}
