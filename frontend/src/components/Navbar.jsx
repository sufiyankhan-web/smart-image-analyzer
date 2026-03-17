import { Aperture } from 'lucide-react';
import { useNavigate, Link } from 'react-router-dom';
import { clearAuthSession, getCurrentUser } from '../utils/auth';

function Navbar() {
  const navigate = useNavigate();
  const user = getCurrentUser();

  const logout = () => {
    clearAuthSession();
    navigate('/login');
  };

  return (
    <header className="sticky top-0 z-40 border-b border-white/5 bg-brand-bg/85 backdrop-blur-xl">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-3">
        <Link to="/" className="flex flex-col items-start select-none group">
          <div className="flex items-center gap-2.5">
            <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-gradient-to-br from-brand-primary to-brand-accent shadow-glow group-hover:shadow-[0_0_32px_rgba(34,211,238,0.25)] transition-all">
              <Aperture className="h-4 w-4 text-white" />
            </div>
            <div className="flex flex-col gap-0.5">
              <span className="text-[13px] font-bold tracking-tight text-white">Smart Image Analyzer</span>
              <span className="text-[9px] text-brand-accent/60 font-medium">LensIQ • Professional Quality Analysis</span>
            </div>
          </div>
          <span className="hidden rounded-full border border-brand-accent/25 bg-brand-accent/10 px-2 py-0.5 text-[10px] font-semibold uppercase tracking-wider text-brand-accentLight sm:inline ml-10 mt-1">
            Beta
          </span>
        </Link>

        <div className="flex items-center gap-3">
          {user?.name && (
            <span className="hidden text-xs text-brand-muted sm:inline">{user.name}</span>
          )}
          <button
            onClick={logout}
            className="rounded-lg border border-white/10 bg-white/5 px-3 py-1.5 text-xs font-medium text-brand-text transition-all hover:bg-white/10 active:scale-95"
          >
            Sign out
          </button>
        </div>
      </div>
    </header>
  );
}

export default Navbar;
