import React, { Component } from "react";
import {
  HelpBlock,
  FormGroup,
  FormControl,
  ControlLabel,
  Button
} from "react-bootstrap";
import LoaderButton from "../components/LoaderButton";
import "./Signup.css";
import Axios from "axios";
import Select from "react-select";
import "./NewSubject.css";
import QRCode from "qrcode.react";
import { Redirect } from "react-router";

const customStyles = {
  option: (provided, state) => ({
    ...provided,
    borderBottom: "1px dotted pink",
    color: state.isSelected ? "red" : "blue",
    padding: 20
  })
};

export default class NewSubject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      uname: "",
      subname: "",
      selectedOption: null,
      options: [],
      qrValue: "",
      id: "2",
      firstname: "Norbert",
      lastname: "Szasz"
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount = () => {
    const loggedId = 10;
    Axios.get(
      "http://localhost:8080/WS/home/subject/demonstrator-subjectList?id=2"
    ).then(response => {
      this.setState({
        options: response.data
      });
    });
  };

  handleChangeSubject = selectedOption => {
    this.setState({ selectedOption });
    console.log(`Option selected:`, selectedOption);
  };

  validateForm() {
    return this.state.subname.length > 0;
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
        "http://localhost:8080/WS/home/subject/registrate",
        //"http://demo7358603.mockable.io/login",
        {
          subjectName: this.state.subname,
          demonstrator: {
            id: this.state.id,
            name: {
              firstName: this.state.firstname,
              lastName: this.state.lastname
            }
          }
        },
        { responseType: "json" }
      ).then(response => {
        console.log(response.data);
        const object = JSON.stringify(response.data);
        JSON.parse(object, (key, value) => {
          alert(value);
        });
      });
      // const data = JSON.parse(response);
      console.log(response);
    } catch (err) {
      console.log(err);
    }
    event.preventDefault();
  }

  getQR = () => {
    const selectedFruit = this.selectedOption;
    Axios.post(
      "http://demo7358603.mockable.io/qr",
      // "http://localhost:8080/WS/home/subject/create-lecture",
      {
        subjectName: this.state.selectedOption.value,
        demonstrator: {
          id: this.state.id,
          name: {
            firstName: this.state.firstname,
            lastName: this.state.lastname
          }
        }
      },
      {
        responseType: "json",
        headers: {
          token: this.props.userStates.token
        }
      }
    )
      .then(response => {
        this.setState({
          qrValue: response.data
        });
      })
      .catch(err => {
        //todo redirect to login
      });
  };

  QRRender = () => {
    if (this.state.qrValue.length > 0) {
      return <QRCode value={this.state.qrValue} size="512" />;
    }
    return "";
  };

  render() {
    if (this.props.userStates.token.length === 0) {
      return <Redirect to="/login" />;
    }

    return (
      <div className="NewSubject">
        <div className="row" />
        <div className="col-md-4">
          <div className="panel panel-default">
            <div className="panel-heading">Tantárgy hozzáadás</div>
            <div className="panel-body">
              <form onSubmit={this.handleSubmit}>
                <FormGroup controlId="subname" bsSize="large">
                  <ControlLabel>Tantárgy neve</ControlLabel>
                  <FormControl
                    autoFocus
                    type="text"
                    value={this.state.subname}
                    onChange={this.handleChange}
                  />
                </FormGroup>
                <Button
                  block
                  bsSize="large"
                  disabled={!this.validateForm()}
                  type="submit"
                >
                  Tantárgy regisztrálása
                </Button>
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="panel panel-default">
            <div className="panel-heading">Tantárgyak listázása</div>
            <div className="panel-body">
              <FormGroup>
                <ControlLabel>Tantárgy neve</ControlLabel>
                <Select
                  value={this.selectedOption}
                  onChange={this.handleChangeSubject}
                  options={this.state.options}
                  styles={customStyles}
                />
              </FormGroup>
              <Button
                block
                bsSize="large"
                onClick={() => {
                  this.getQR();
                }}
              >
                QR kod lekérdezése
              </Button>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-4 col-md-offset-4">{this.QRRender()}</div>
        </div>
      </div>
    );
  }
}
