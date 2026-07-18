export default function FooterSection() {
  return (
    <footer>
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-sm-12">
            <p>Copyright &copy; {new Date().getFullYear()} New Event</p>
            <ul className="social-icon">
              <li><a href="#" className="fa fa-facebook" /></li>
              <li><a href="#" className="fa fa-twitter" /></li>
              <li><a href="#" className="fa fa-dribbble" /></li>
              <li><a href="#" className="fa fa-behance" /></li>
              <li><a href="#" className="fa fa-google-plus" /></li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  )
}
