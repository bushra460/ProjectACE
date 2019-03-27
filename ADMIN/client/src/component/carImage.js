import React from "react";
import ReactTable from '../../node_modules/react-table';


class CarImage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            carImages: [],
            columns: [
            {
              Header: "Car Images",
              columns: [
                {
                  Header: 'Id',
                  accessor: 'carImageId',
                  maxWidth: 40
                },
                {
                  Header: "URL",
                  id: "uri",
                  accessor: d => d.uri
                },
                {
                  Header: "Exterior Image",
                  id: "exteriorImage",
                  maxWidth: 110,
                  accessor: d => d.exteriorImage.toString()
                },
                {
                  Header: "Active",
                  id: "active",
                  maxWidth: 80,
                  accessor: d => d.active.toString()
                }
              ]
            }
          ]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.carImages.length)
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.carImages.length > 0 ? 
                <ReactTable
                data={this.props.carImages}
                columns={this.state.columns}
                defaultPageSize = {5}
                pageSizeOptions = {[5, 10, 15, 20]}
                className="-striped -highlight"
                /> : null}
            </div>
        );
      }
}

export default CarImage;