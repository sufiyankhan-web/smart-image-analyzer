import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Aperture, Loader2, Target, Sun, Layout, Waves, Shield } from 'lucide-react';
import { loginCustomer } from '../services/api';
import { saveAuthSession } from '../utils/auth';

const HIGHLIGHTS = [
  { Icon: Target, label: 'Sharpness',   desc: 'Subject-weighted 70/30 Laplacian scoring' },
  { Icon: Sun,    label: 'Exposure',    desc: 'Histogram highlight & shadow detection' },
  { Icon: Layout, label: 'Composition', desc: 'Rule-of-thirds + golden ratio overlays' },
  { Icon: Waves,  label: 'Noise',       desc: 'Gaussian residual scene-aware estimation' },
];

function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ email: 'customer@smartphoto.ai', password: 'Customer@123' });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const onSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      const response = await loginCustomer(form);
      saveAuthSession(response);
      navigate(location.state?.from || '/', { replace: true });
    } catch (apiError) {
      setError(apiError.response?.data?.message || 'Login failed. Please check credentials.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="relative flex min-h-screen overflow-hidden">
      {/* Ambient background */}
      <div className="pointer-events-none absolute inset-0">
        <div className="hero-grid-bg h-full w-full" />
        <div className="absolute -left-40 -top-40 h-[500px] w-[500px] rounded-full bg-brand-primary/10 blur-[120px]" />
        <div className="absolute -bottom-40 -right-40 h-[500px] w-[500px] rounded-full bg-brand-accent/[0.08] blur-[120px]" />
      </div>

      {/* Left panel (desktop only) */}
      <div className="relative hidden flex-1 flex-col justify-between p-12 lg:flex">
        <div className="flex items-center gap-2.5">
          <div className="flex h-9 w-9 items-center justify-center rounded-xl bg-gradient-to-br from-brand-primary to-brand-accent shadow-glow">
            <Aperture className="h-5 w-5 text-white" />
          </div>
          <span className="text-lg font-bold tracking-tight text-white">LensIQ</span>
          <span className="rounded-full border border-brand-accent/25 bg-brand-accent/10 px-2 py-0.5 text-[10px] font-semibold uppercase tracking-wider text-brand-accentLight">
            Beta
          </span>
        </div>

        <div className="space-y-8">
          <div>
            <h1 className="text-4xl font-extrabold leading-tight text-white xl:text-5xl">
              Photography<br />
              <span className="gradient-text">Intelligence</span><br />
              at a Glance
            </h1>
            <p className="mt-4 max-w-sm text-base text-brand-muted">
              Upload any image and get a professional quality breakdown in seconds, powered by OpenCV and Spring Boot.
            </p>
          </div>

          <div className="grid grid-cols-2 gap-3">
            {HIGHLIGHTS.map(({ Icon, label, desc }) => (
              <div key={label} className="rounded-xl border border-white/5 bg-brand-panel/60 p-4 backdrop-blur">
                <div className="mb-2 flex h-8 w-8 items-center justify-center rounded-lg bg-brand-accent/10">
                  <Icon className="h-4 w-4 text-brand-accentLight" />
                </div>
                <p className="text-xs font-semibold text-brand-text">{label}</p>
                <p className="mt-0.5 text-[11px] leading-relaxed text-brand-muted">{desc}</p>
              </div>
            ))}
          </div>
        </div>

        <div className="flex items-center gap-2 text-xs text-brand-muted">
          <Shield className="h-3.5 w-3.5 text-brand-accent/60" />
          <span>9 analysis modules · Real-time scoring · Visual overlays</span>
        </div>
      </div>

      {/* Right panel (login form) */}
      <div className="relative z-10 flex w-full flex-col items-center justify-center px-6 py-12 lg:w-[460px] lg:border-l lg:border-white/5 lg:bg-brand-bg/60 lg:backdrop-blur-2xl">
        {/* Mobile logo */}
        <div className="mb-8 flex items-center gap-2.5 lg:hidden">
          <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-gradient-to-br from-brand-primary to-brand-accent shadow-glow">
            <Aperture className="h-4 w-4 text-white" />
          </div>
          <span className="text-base font-bold tracking-tight text-white">LensIQ</span>
        </div>

        <form onSubmit={onSubmit} className="w-full max-w-sm space-y-6">
          <div>
            <h2 className="text-2xl font-bold text-white">Welcome back</h2>
            <p className="mt-1 text-sm text-brand-muted">Sign in to your dashboard.</p>
          </div>

          <div className="space-y-3">
            <div>
              <label className="mb-1.5 block text-xs font-medium text-brand-mutedLight">
                Email address
              </label>
              <input
                type="email"
                required
                value={form.email}
                onChange={(e) => setForm((p) => ({ ...p, email: e.target.value }))}
                className="w-full rounded-xl border border-white/[0.08] bg-brand-panelSoft px-3.5 py-2.5 text-sm text-brand-text outline-none placeholder:text-brand-muted focus:border-brand-accent/50 focus:ring-1 focus:ring-brand-accent/30 transition-colors"
                placeholder="you@example.com"
              />
            </div>
            <div>
              <label className="mb-1.5 block text-xs font-medium text-brand-mutedLight">
                Password
              </label>
              <input
                type="password"
                required
                value={form.password}
                onChange={(e) => setForm((p) => ({ ...p, password: e.target.value }))}
                className="w-full rounded-xl border border-white/[0.08] bg-brand-panelSoft px-3.5 py-2.5 text-sm text-brand-text outline-none placeholder:text-brand-muted focus:border-brand-accent/50 focus:ring-1 focus:ring-brand-accent/30 transition-colors"
                placeholder="••••••••"
              />
            </div>
          </div>

          {error && (
            <div className="rounded-xl border border-red-500/20 bg-red-500/[0.08] px-4 py-3 text-sm text-red-300">
              {error}
            </div>
          )}

          <button
            type="submit"
            disabled={submitting}
            className="flex w-full items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-brand-accent to-brand-primary py-2.5 text-sm font-semibold text-white shadow-glow transition-opacity hover:opacity-90 disabled:opacity-60"
          >
            {submitting && <Loader2 className="h-4 w-4 animate-spin" />}
            {submitting ? 'Signing in...' : 'Sign in'}
          </button>

          <p className="text-center text-[11px] text-brand-muted">
            Demo credentials are pre-filled above.
          </p>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;
