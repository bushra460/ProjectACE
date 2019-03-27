import React from "react";
import ReactTable from '../../node_modules/react-table';

class HotspotDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          hotspotDetails: [],
            columns: [
            {
            Header: "Hotspot Details",
            columns: [
              {
                Header: 'Id',
                accessor: 'hotspotDetailId',
                maxWidth: 40
              },
              {
                Header: "URL",
                id: "uri",
                accessor: d => d.uri
              },
              {
                Header: "Notes",
                id: "notes",
                accessor: d => d.notes
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
        console.log(this.props.hotspotDetails.length)
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.hotspotDetails.length > 0 ? 
                <ReactTable
                data={this.props.hotspotDetails}
                columns={this.state.columns}
                defaultPageSize = {5}
                pageSizeOptions = {[5, 10, 15, 20]}
                className="-striped -highlight"
                /> : null}
            </div>
        );
      }
}

export default HotspotDetail;