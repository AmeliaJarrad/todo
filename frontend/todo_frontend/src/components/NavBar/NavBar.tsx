import { NavLink } from 'react-router-dom';
import classes from './NavBar.module.scss';

export default function NavBar() {
  return (
    <nav className={classes.nav}>
      <div className={classes.nav_group}>
        <NavLink className={classes.nav_link} to="/">
          All Tasks
        </NavLink>
      </div>
      <div className={classes.nav_group}>
        <NavLink className={classes.nav_link} to="/archived">
          Archived Tasks
        </NavLink>
        <NavLink className={classes.nav_link} to="/tasks/new">
          Add New Task
        </NavLink>
      </div>
    </nav>
  );
}