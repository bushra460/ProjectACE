import React from "react";
import ReactTable from '../../node_modules/react-table';


class ModelYear extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modelYears: [],
            columns:[
              {
                Header: "Model Years",
                columns: [
                  {
                    Header: 'Id',
                    accessor: 'modelYearId',
                    maxWidth: 40
                  },
                  {
                    
                    Header: "Year",
                    id: "yearValue",
                    accessor: d => d.yearValue
                  }
                ]
              }
            ]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.modelYears.length)
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.modelYears.length > 0 ? 
                  <ReactTable
                  data={this.props.modelYears}
                  columns={this.state.columns}
                  defaultPageSize = {5}
                  pageSizeOptions = {[5, 10, 15, 20]}
                  className="-striped -highlight"
                  /> : null}
            </div>
        );
      }
}

export default ModelYear;