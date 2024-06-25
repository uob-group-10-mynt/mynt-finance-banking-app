import Footer from './Footer';
import Header from './Header';

function layout({ children }){
    return(
        <div>
            <Header></Header>
            <main>
                hello world
                {children}
            </main> 
            <Footer></Footer>
        </div>
    );
}

export default layout;